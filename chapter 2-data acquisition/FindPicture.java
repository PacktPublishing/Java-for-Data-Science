package packt.flickrdemonstration;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.photos.Size;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Collection;
import javax.imageio.ImageIO;

public class FindPicture {

    public FindPicture() {
        try {
            String apikey = "Your API key";
            String secret = "Your secret";

            Flickr flickr = new Flickr(apikey, secret, new REST());

            SearchParameters searchParameters = new SearchParameters();
            searchParameters.setBBox("-180", "-90", "180", "90");
            searchParameters.setMedia("photos");
            PhotoList<Photo> list = flickr.getPhotosInterface().search(searchParameters, 10, 0);

            out.println("Image List");
            for (int i = 0; i < list.size(); i++) {
                Photo photo = list.get(i);
                out.println("Image: " + i
                        + "\nTitle: " + photo.getTitle()
                        + "\nMedia: " + photo.getOriginalFormat()
                        + "\nPublic: " + photo.isPublicFlag()
                        + "\nPublic: " + photo.isPublicFlag()
                        + "\nUrl: " + photo.getUrl()
                        + "\n");
            }
            out.println();

            PhotosInterface pi = new PhotosInterface(apikey, secret, new REST());
            out.println("pi: " + pi);
            Photo currentPhoto = list.get(0);
            out.println("currentPhoto url: " + currentPhoto.getUrl());

            // Get image using URL
            BufferedImage bufferedImage = pi.getImage(currentPhoto.getUrl());
            out.println("bi: " + bufferedImage);

            // Get image using Photo instance
            bufferedImage = pi.getImage(currentPhoto, Size.SMALL);

            // Save image to file
            out.println("bufferedImage: " + bufferedImage);
            File outputfile = new File("image.jpg");
            ImageIO.write(bufferedImage, "jpg", outputfile);
        } catch (FlickrException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FindPicture();
    }

    public void displaySizes(Photo photo) {
        out.println("---Sizes----");
        Collection<Size> sizes = photo.getSizes();
        for (Size size : sizes) {
            out.println(size.getLabel());
        }
    }
}
