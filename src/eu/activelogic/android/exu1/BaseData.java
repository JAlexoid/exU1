package eu.activelogic.android.exu1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.util.Log;

import eu.activelogic.android.exu1.dummy.UbuntuOneAPI;
import eu.activelogic.android.uone.files.UOBaseNode;
import eu.activelogic.android.uone.files.UONode;
import eu.activelogic.android.uone.files.UOVolumes;

public class BaseData {

    private static BaseData baseData = new BaseData();

    private UbuntuOneAPI api;

    List<UOBaseNode> baseNodes = new ArrayList<UOBaseNode>();

    private ExecutorService executor;

    private BaseData() {
	executor = Executors.newCachedThreadPool();
    }

    public static BaseData getInstance() {
	return baseData;
    }
    
    public void initTokens(String oAuthTokens){
	String[] keys = oAuthTokens.split(":");
	Log.d("BD", Arrays.toString(keys));
	api = new UbuntuOneAPI(keys[0], keys[1], keys[2], keys[3]);
    }

    public void notifyMeLater(MessageConsumer<?> consumer) {
	executor.submit(new SleepAndNotify(consumer));
    }

    public Future<UOVolumes> loadVolumes(MessageConsumer<UOVolumes> consumer) {
	VolumeLoader loader = new VolumeLoader(consumer, api);
	return executor.submit(loader);
    }

    public Future<UONode> loadNode(String path, MessageConsumer<String> consumer) {
	return executor.submit(new NodeLoader(path, consumer, api));
    }

    private abstract class BaseLoader<E, T> implements Callable<T> {
	final MessageConsumer<E> consumer;
	final UbuntuOneAPI api;

	public BaseLoader(MessageConsumer<E> consumer, UbuntuOneAPI api) {
	    super();
	    this.consumer = consumer;
	    this.api = api;
	}

	protected void sendMessage(E entity) {
	    try {
		consumer.consumeMessage(entity);
	    } catch (Exception e) {
		Log.e("BaseData", e.getMessage(), e);
	    }
	}

    }

    private class NodeLoader extends BaseLoader<String, UONode> {

	private final String path;

	public NodeLoader(String path, MessageConsumer<String> consumer, UbuntuOneAPI api) {
	    super(consumer, api);
	    this.path = path;
	}

	@Override
	public UONode call() throws Exception {
	    UONode node = api.fetchNode(path, true);
	    sendMessage(node.getName());
	    return node;
	}

    }

    private class VolumeLoader extends BaseLoader<UOVolumes, UOVolumes> {

	public VolumeLoader(MessageConsumer<UOVolumes> consumer, UbuntuOneAPI api) {
	    super(consumer, api);
	}

	@Override
	public UOVolumes call() throws Exception {
	    UOVolumes volumes = api.listVolumes();

	    List<UOBaseNode> newList = new ArrayList<UOBaseNode>();

	    newList.add(new UOBaseNode(volumes.rootNodePath));

	    for (String path : volumes.userNodePaths)
		newList.add(new UOBaseNode(path));

	    baseNodes = Collections.unmodifiableList(newList);

	    sendMessage(volumes);

	    return volumes;
	}

    }

    private class SleepAndNotify implements Runnable {
	private final MessageConsumer<?> consumer;

	public SleepAndNotify(MessageConsumer<?> consumer) {
	    super();
	    this.consumer = consumer;
	}

	@Override
	public void run() {
	    try {
		Thread.sleep(1000l);
	    } catch (InterruptedException e) {
		// You see nothing!!!
	    } finally {
		consumer.consumeMessage(null);
	    }

	}

    }

    public List<UOBaseNode> getBaseNodes() {
	return baseNodes;
    }

}
