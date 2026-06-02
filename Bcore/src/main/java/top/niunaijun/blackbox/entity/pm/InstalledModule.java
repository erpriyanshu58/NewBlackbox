package top.niunaijun.blackbox.entity.pm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents an installed module in the BlackBox virtual environment.
 * This Parcelable is referenced by InstalledModule.aidl.
 */
public class InstalledModule implements Parcelable {

    public String packageName;
    public String apkPath;
    public boolean enabled;
    public String description;
    public int userId;

    public InstalledModule() {
    }

    public InstalledModule(String packageName, String apkPath, boolean enabled, String description, int userId) {
        this.packageName = packageName;
        this.apkPath = apkPath;
        this.enabled = enabled;
        this.description = description;
        this.userId = userId;
    }

    protected InstalledModule(Parcel in) {
        this.packageName = in.readString();
        this.apkPath = in.readString();
        this.enabled = in.readByte() != 0;
        this.description = in.readString();
        this.userId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.apkPath);
        dest.writeByte(this.enabled ? (byte) 1 : (byte) 0);
        dest.writeString(this.description);
        dest.writeInt(this.userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InstalledModule> CREATOR = new Creator<InstalledModule>() {
        @Override
        public InstalledModule createFromParcel(Parcel in) {
            return new InstalledModule(in);
        }

        @Override
        public InstalledModule[] newArray(int size) {
            return new InstalledModule[size];
        }
    };

    @Override
    public String toString() {
        return "InstalledModule{" +
                "packageName='" + packageName + '\'' +
                ", apkPath='" + apkPath + '\'' +
                ", enabled=" + enabled +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
