package com.bignerdranch.android.nerdlauncher;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.provider.*;
import android.support.v4.app.*;
import android.text.*;
import android.text.format.*;
import android.view.*;
import android.util.Log;
import android.widget.*;

public class NerdLauncherFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	List<ResolveInfo> activities = getActivities();
	sortActivitiesByName(activities);

	ArrayAdapter<ResolveInfo> adapter = getActivitiesArrayAdapter(activities);
	setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
	ResolveInfo resolveInfo = (ResolveInfo) listView.getAdapter().getItem(position);
	ActivityInfo activityInfo = resolveInfo.activityInfo;

	if (activityInfo != null) {
	    Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
    }

    private List<ResolveInfo> getActivities() {
	Intent startupIntent = new Intent(Intent.ACTION_MAIN);
	startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

	PackageManager pm = getActivity().getPackageManager();
	return pm.queryIntentActivities(startupIntent, 0);
    }

    private void sortActivitiesByName(List<ResolveInfo> activities) {
	Comparator comparator = new Comparator<ResolveInfo>() {
	    public int compare(ResolveInfo a, ResolveInfo b){
		PackageManager pm = getActivity().getPackageManager();
		return String.CASE_INSENSITIVE_ORDER.compare(
		    a.loadLabel(pm).toString(),
		    b.loadLabel(pm).toString()
		);
	    }
	};
	Collections.sort(activities, comparator);
    }

    private ArrayAdapter<ResolveInfo> getActivitiesArrayAdapter(List<ResolveInfo> activities) {
	return new ArrayAdapter<ResolveInfo>(getActivity(), android.R.layout.simple_list_item_1, activities) {
	    public View getView(int pos, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.icon_with_text_list_item, null);
                }

		PackageManager pm = getActivity().getPackageManager();
                ResolveInfo resolveInfo = getItem(pos);

		TextView textView = (TextView) convertView.findViewById(R.id.icon_with_text_list_item_text);
		textView.setText(resolveInfo.loadLabel(pm));

                ImageView imageView = (ImageView) convertView.findViewById(R.id.icon_with_text_list_item_icon);
                imageView.setImageDrawable(resolveInfo.loadIcon(pm));

		return convertView;
	    }
	};
    }
}
