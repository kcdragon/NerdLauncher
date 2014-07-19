package com.bignerdranch.android.nerdlauncher;

import android.os.*;
import android.support.v4.app.*;

public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
	return new NerdLauncherFragment();
    }
}
