package speedata.com.eoms.model;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serializable;

import speedata.com.eoms.utils.BimpUtil;

/**
 * Created by YJ on 16/4/27.
 */
public class ImageItem implements Serializable {

    private static final long serialVersionUID = 1L;
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    private Bitmap bitmap;
    public boolean isSelected = false;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) {
            try {
                bitmap = BimpUtil.revitionImageSize(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void recycleBitmap() {
        if (bitmap != null&& !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }
    }

}
