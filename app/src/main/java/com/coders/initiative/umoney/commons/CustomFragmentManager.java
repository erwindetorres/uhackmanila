package com.coders.initiative.umoney.commons;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.coders.initiative.umoney.MainActivity;

import java.util.HashMap;

/**
 * Created by Kira on 8/27/2016.
 */
public class CustomFragmentManager {

    private MainActivity activity;
    private final HashMap<String, Fragment> fragments = new HashMap<>();
    private final FragmentManager fragmentManager;

    private final int container;
    private String current;

    public CustomFragmentManager(MainActivity mainActivity, @IdRes int container) {
        this.activity = mainActivity;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.container = container;
    }


    public void switchTo(Class<? extends Fragment> fragment, @Nullable Bundle b) {
        String name = fragment.getName();

        if (current != null) {
            if(current==name){


            }else{
                fragmentManager.beginTransaction().remove(fragments.get(current)).commit();
            }

        }

        if (fragmentManager.findFragmentByTag(name) == null) {

            Fragment instance = Fragment.instantiate(activity, name);
            if(b!=null){
                instance.setArguments(b);
            }
            fragments.put(name, instance);
            fragmentManager.beginTransaction().add(container, instance, name).commit();

        } else {
            if(b!=null){
                fragments.get(name).setArguments(b);
            }
            fragmentManager.beginTransaction().show(fragments.get(name)).commit();
            //fragmentManager.beginTransaction().replace(container, fragments.get(name)).commit();
        }

        current = name;
    }


    @Nullable
    public Fragment getFragment(Class<? extends Fragment> fragment) {
        return fragments.get(fragment.getName());
    }

    public String getCurrentFragment(){
        return current;

    }
    public FragmentManager getFragmentManager(){
        return  fragmentManager;
    }
}
