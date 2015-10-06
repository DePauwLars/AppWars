package appwars.appwise.be.appwars;

import android.graphics.drawable.Drawable;

/**
 * Created by Lakkedelakke on 6/10/2015.
 */
public class App {
    private String name;
    private Drawable icon;

    public App(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
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
