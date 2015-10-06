package appwars.appwise.be.appwars;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lakkedelakke on 6/10/2015.
 */
public class PackageInformation {

    private Context mContext;

    public  PackageInformation(Context context){
        mContext=context;
    }


    public class InfoObject {
        public String appname = "";
        public String pname = "";
        public String versionName = "";
        public int versionCode = 0;
        public Drawable icon;


        public void InfoObjectAggregatePrint() {//not used yet
            Log.v(appname, appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
        }

    }
    private ArrayList<InfoObject> getPackages() {
        ArrayList<InfoObject> apps = getInstalledApps(false);
        final int max = apps.size();
        for (int i = 0; i < apps.size(); i++) {
            Log.e("appname", apps.get(i).appname);
            Log.e("icon", apps.get(i).icon.toString());
        }
        return apps;
    }

    public ArrayList<InfoObject> getInstalledApps(boolean getSysPackages) {
        ArrayList<InfoObject> res = new ArrayList<InfoObject>();
        List<PackageInfo> packs = mContext.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            InfoObject newInfo = new InfoObject();
            newInfo.appname = p.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(mContext.getPackageManager());
            res.add(newInfo);
        }
        return res;
    }


}