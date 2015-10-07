package appwars.appwise.be.appwars;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lakkedelakke on 6/10/2015.
 */
public class App {
    private String name;
    private Drawable icon;
    private boolean isChecked;

    public App(String name, Drawable icon, boolean isChecked) {
        this.name = name;
        this.icon = icon;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

}
