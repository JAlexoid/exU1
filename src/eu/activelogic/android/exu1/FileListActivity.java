package eu.activelogic.android.exu1;

import java.io.IOException;
import java.util.Arrays;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentTransaction;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.View;
import eu.activelogic.android.uone.files.UOBaseNode;
import eu.activelogic.android.uone.files.UOVolumes;

/**
 * An activity representing a list of Files. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a {@link FileDetailActivity} representing
 * item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a {@link FileListFragment} and
 * the item details (if present) is a {@link FileDetailFragment}.
 * <p>
 * This activity also implements the required {@link FileListFragment.Callbacks} interface to listen
 * for item selections.
 */
public class FileListActivity extends Activity implements FileListFragment.Callbacks, MessageConsumer<UOVolumes> {

    private static final String TAG = "FLA";

    public static final String KEY_AUTHTOKEN = "ubuntuone";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public FileListFragment fileListFragment;
    public LoadingFragment loadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	Log.d(TAG, "onCreate");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_file_list);

	fileListFragment = (FileListFragment) getFragmentManager().findFragmentById(R.id.file_list);
	loadingFragment = new LoadingFragment();

	loadAuthToken();

	FragmentTransaction ft = getFragmentManager().beginTransaction();
	ft.replace(R.id.main_list_container, loadingFragment);
	ft.disallowAddToBackStack();
	ft.commit();

	if (findViewById(R.id.file_detail_container) != null) {
	    // The detail container view will be present only in the
	    // large-screen layouts (res/values-large and
	    // res/values-sw600dp). If this view is present, then the
	    // activity should be in two-pane mode.
	    mTwoPane = true;

	    // In two-pane mode, list items should be given the
	    // 'activated' state when touched.
	    fileListFragment.setActivateOnItemClick(true);

	    FragmentBreadCrumbs fb = (FragmentBreadCrumbs) getLayoutInflater().inflate(R.layout.breadcrumbs, null);

	    getActionBar().setCustomView(fb);
	    getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

	    fb.setActivity(this);
	    fb.setParentTitle("AA", "aa", null);
	    fb.setTitle("BB", "bb");

	}

	// BaseData.getInstance().loadVolumes(this);

    }

    private void loadAuthToken() {

	final AccountManager am = AccountManager.get(this);
	Log.d(TAG, "Accounts: " + Arrays.toString(am.getAccountsByType("com.ubuntu")));

	Account[] accs = am.getAccountsByType("com.ubuntu");

	if (accs != null && accs.length > 0) {
	    final Account account = accs[0];

	    final AccountManagerFuture<Bundle> future = am.getAuthToken(account, KEY_AUTHTOKEN, null, true, null, null);

	    new Thread() {
		public void run() {
		    String oAuth;
		    try {
			Bundle b = future.getResult();

			if (b.containsKey(AccountManager.KEY_INTENT)) {
			    final Intent i = (Intent) b.get(AccountManager.KEY_INTENT);
			    i.setFlags(0);
			    startActivityForResult(i, 1337);
			    oAuth = i.toString() + " -> " + i.getExtras().keySet();
			} else if (b.containsKey(AccountManager.KEY_AUTHTOKEN)) {
			    oAuth = b.getString(AccountManager.KEY_AUTHTOKEN);
			    BaseData.getInstance().initTokens(oAuth);
			    BaseData.getInstance().loadVolumes(FileListActivity.this);
			} else {
			    oAuth = "Unknown keys";
			}

		    } catch (OperationCanceledException e) {
			oAuth = e.getMessage();
		    } catch (AuthenticatorException e) {
			oAuth = e.getMessage();
		    } catch (IOException e) {
			oAuth = e.getMessage();
		    }
		    Log.d(TAG, "OAuth: " + oAuth);
		};
	    }.start();
	} else {
	    
	    Intent i = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.ubuntu"}, false, null,  null, null, null);
	    
//	    Intent i = new Intent();
//	    i.setPackage("com.ubuntuone.android.files");
//	    i.setClassName("com.ubuntuone.android.files", "com.ubuntuone.android.files.activity.LoginActivity");
	    i.setFlags(0);
	    startActivityForResult(i, 1337);
	}
    }

    @Override
    protected void onRestart() {
	Log.d(TAG, "onRestart");
	super.onRestart();
    }

    @Override
    protected void onStop() {
	Log.d(TAG, "onStop");
	super.onStop();
    }

    @Override
    protected void onStart() {
	Log.d(TAG, "onStart");
	super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	Log.d(TAG, "onActivityResult " + data);
	if (requestCode == 1337) {
	    if (Activity.RESULT_OK == resultCode) {
		loadAuthToken();
	    } else if (Activity.RESULT_CANCELED == resultCode) {
		Log.d(TAG, "Result cancelled");
	    } else {
		Log.d(TAG, "Result was fail");
	    }
	}
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
	Log.d(TAG, "onActionModeFinished");
	super.onActionModeFinished(mode);
    }

    public void initialDataLoaded() {

	FragmentTransaction ft = getFragmentManager().beginTransaction();
	ft.replace(R.id.main_list_container, fileListFragment);
	ft.disallowAddToBackStack();
	ft.commit();

    }

    /**
     * Callback method from {@link FileListFragment.Callbacks} indicating that
     * the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(UOBaseNode node) {
	if (mTwoPane) {

	    Bundle arguments = new Bundle();
	    arguments.putString(FileDetailFragment.ARG_ITEM_ID, node.path);
	    FileDetailFragment fragment = new FileDetailFragment();
	    fragment.setArguments(arguments);

	    for (int i = getFragmentManager().getBackStackEntryCount(); i > 0; i--)
		getFragmentManager().popBackStackImmediate();

	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    ft.addToBackStack(null);
	    ft.replace(R.id.file_detail_container, fragment);
	    ft.commit();

	} else {
	    // In single-pane mode, simply start the detail activity
	    // for the selected item ID.
	    Intent detailIntent = new Intent(this, FileDetailActivity.class);
	    detailIntent.putExtra(FileDetailFragment.ARG_ITEM_ID, node.path);
	    startActivity(detailIntent);
	}
    }

    @Override
    public void consumeMessage(UOVolumes message) {
	super.runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		initialDataLoaded();
	    }
	});
	Log.d(TAG, "Paths: " + message.userNodePaths);
    }

}
