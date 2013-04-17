package eu.activelogic.android.exu1;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import eu.activelogic.android.ui.CheckableLayout;
import eu.activelogic.android.uone.files.UONode;

/**
 * A fragment representing a single File detail screen. This fragment is either
 * contained in a {@link FileListActivity} in two-pane mode (on tablets) or a
 * {@link FileDetailActivity} on handsets.
 */
public class FileDetailFragment extends Fragment implements MessageConsumer<String> {

    public static final String TAG = "FDF";

    GridView mGrid;

    public int height = 72, width = 72;

    private Activity context;

    LoadingFragment loadingFragment;

    private Future<UONode> future;

    private UONode rootNode;

    private Drawable image;
    private Drawable folder;
    private Drawable folderFull;

    protected Map<String, Drawable> iconMap = new HashMap<String, Drawable>();

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FileDetailFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);
	context = activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Log.d(TAG, "onCreate");

	height = (int) (getResources().getDisplayMetrics().density * height);
	width = (int) (getResources().getDisplayMetrics().density * width);

	loadingFragment = new LoadingFragment();

	image = context.getResources().getDrawable(R.drawable.icon_unknown);
	folder = context.getResources().getDrawable(R.drawable.icon_folder);
	folderFull = context.getResources().getDrawable(R.drawable.icon_folder_open);
	if (future == null) {
	    if (getArguments().containsKey(ARG_ITEM_ID)) {
		future = BaseData.getInstance().loadNode(getArguments().getString(ARG_ITEM_ID), this);
	    }

	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.replace(R.id.file_detail_container, loadingFragment);
	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    ft.disallowAddToBackStack();
	    ft.commit();
	}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	mGrid = (GridView) inflater.inflate(R.layout.fragment_file_detail, container, false);

	mGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
	mGrid.setAdapter(new IconsAdapter());
	mGrid.setMultiChoiceModeListener(new MultiChoiceModeListener());
	mGrid.setOnItemClickListener(new ItemClickListener());

	return mGrid;
    }

    public void dataLoaded(String message) {
	if (Looper.myLooper() != Looper.getMainLooper())
	    return;

	Log.d(TAG, "DataLoaded with future state: " + future.isDone());

	try {
	    this.rootNode = future.get(50, TimeUnit.MILLISECONDS);

	    Collections.sort(this.rootNode.children, new DirectoryComparator());

	    Toast.makeText(context, "Loaded data", Toast.LENGTH_SHORT).show();

	    mGrid.invalidate();
	    mGrid.invalidateViews();

	    FragmentTransaction ft = context.getFragmentManager().beginTransaction();
	    ft.replace(R.id.file_detail_container, this);
	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    // ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
	    // android.R.animator.fade_in, android.R.animator.fade_out);
	    // ft.setBreadCrumbShortTitle(rootNode.resourcePath.replace(rootNode.parentPath != null
	    // ? rootNode.parentPath : "/~/", "").replaceFirst("\\/", ""));
	    ft.setBreadCrumbTitle(rootNode.resourcePath.replace(rootNode.parentPath != null ? rootNode.parentPath : "/~/", "").replaceFirst("\\/", ""));
	    ft.disallowAddToBackStack();
	    ft.commit();

	} catch (InterruptedException e) {
	    Log.e(TAG, "InterruptedException", e);
	    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show();
	} catch (ExecutionException e) {
	    Log.e(TAG, "ExecutionException", e);
	    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show();
	} catch (TimeoutException e1) {
	    BaseData.getInstance().notifyMeLater(this);
	    Log.d(TAG, "Taking a nap!");
	}

    }

    @Override
    public void consumeMessage(final String message) {
	context.runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		dataLoaded(message);
	    }
	});
    }

    public class IconsAdapter extends BaseAdapter {

	public View getView(int position, View convertView, ViewGroup parent) {
	    CheckableLayout l;
	    ImageView i;
	    TextView tv;

	    if (convertView == null) {
		l = (CheckableLayout) LayoutInflater.from(context).inflate(R.layout.file_element, null);
		i = (ImageView) l.getChildAt(0);
		tv = (TextView) l.getChildAt(1);
	    } else {
		l = (CheckableLayout) convertView;
		i = (ImageView) l.getChildAt(0);
		tv = (TextView) l.getChildAt(1);
	    }

	    UONode node = rootNode.children.get(position);

	    tv.setText(node.resourcePath.replace(node.parentPath, "").replaceFirst("\\/", ""));
	    if (node.kind == UONode.Kind.DIRECTORY) {
		if (node.hasChildren)
		    i.setImageDrawable(folderFull);
		else
		    i.setImageDrawable(folder);
	    } else {
		String icon = MimeTypeIcons.findIconForFile(node.path);

		if (icon == null) {
		    i.setImageDrawable(image);
		} else {
		    if (!iconMap.containsKey(icon)) {
			Drawable iconImage;
			try {
			    iconImage = BitmapDrawable.createFromStream(context.getAssets().open("icons/" + icon), icon);
			} catch (IOException e) {
			    iconImage = image;
			}
			iconMap.put(icon, iconImage);
		    }

		    i.setImageDrawable(iconMap.get(icon));
		}
	    }

	    return l;
	}

	public final int getCount() {
	    if (rootNode != null)
		return rootNode.children.size();
	    else
		return 0;
	}

	public final Object getItem(int position) {
	    return rootNode.children.get(position);
	}

	public final long getItemId(int position) {
	    return position;
	}
    }

    public class ItemClickListener implements OnItemClickListener {
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	    UONode node = rootNode.children.get(position);

	    if (node.kind == UONode.Kind.DIRECTORY) {

		Bundle arguments = new Bundle();
		arguments.putString(FileDetailFragment.ARG_ITEM_ID, node.resourcePath);
		FileDetailFragment fragment = new FileDetailFragment();
		fragment.setArguments(arguments);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		// ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
		// android.R.animator.fade_in, android.R.animator.fade_out);
		ft.addToBackStack(null);
		ft.setBreadCrumbTitle(node.path);
		ft.replace(R.id.file_detail_container, fragment);
		ft.commit();
	    }
	}
    }

    public class MultiChoiceModeListener implements GridView.MultiChoiceModeListener {
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	    mode.setTitle("Select Items");
	    mode.setSubtitle("One item selected");
	    return true;
	}

	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	    return true;
	}

	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    return true;
	}

	public void onDestroyActionMode(ActionMode mode) {
	}

	public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
		boolean checked) {
	    int selectCount = mGrid.getCheckedItemCount();
	    switch (selectCount) {
	    case 1:
		mode.setSubtitle("One item selected");
		break;
	    default:
		mode.setSubtitle("" + selectCount + " items selected");
		break;
	    }
	}

    }

    private static class DirectoryComparator implements Comparator<UONode> {

	@Override
	public int compare(UONode lhs, UONode rhs) {
	    if (lhs.kind != rhs.kind) {
		return lhs.kind == UONode.Kind.DIRECTORY ? -1 : 1;
	    } else {
		return lhs.resourcePath.compareToIgnoreCase(rhs.resourcePath);
	    }
	}

    }

}
