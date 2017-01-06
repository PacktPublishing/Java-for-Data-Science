package opencvnonmavenexamples;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import static org.opencv.core.CvType.CV_8UC1;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVNonMavenExamples {

    public OpenCVNonMavenExamples() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        enhanceImageBrightness();
        enhanceImageContrast();
//        sharpenImage();
        smoothImage();
        resizeImage();
        convertImage();
//        noiseRemoval();
//        denoise();
//        convertToTIFF();
    }

    public static void main(String[] args) {
        new OpenCVNonMavenExamples();
    }

    // Histogram equalization is used to improve the overall appearnace of an image. 
    // http://docs.opencv.org/2.4/doc/tutorials/imgproc/histograms/histogram_equalization/histogram_equalization.html
    // From: http://www.tutorialspoint.com/java_dip/enhancing_image_contrast.htm
    // Enhancing grayscale images with histogram equalization.
    public void enhanceImageContrast() {
        Mat source = Imgcodecs.imread("GrayScaleParrot.png",
                Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.equalizeHist(source, destination);
        Imgcodecs.imwrite("enhancedParrot.jpg", destination);
    }

    public void smoothImage() {
        // Smoothing, also called blurring, will make the edges soother.
        Mat source = Imgcodecs.imread("cat.jpg");
        Mat destination = source.clone();
        for (int i = 0; i < 25; i++) {
            Mat sourceImage = destination.clone();
            Imgproc.blur(sourceImage, destination, new Size(3.0, 3.0));
        }
        Imgcodecs.imwrite("smoothCat.jpg", destination);
    }

    public void sharpenImage() {
        String fileName = "SharpnessExample2.png";
        fileName = "smoothCat.jpg";
        fileName = "blurredText.jpg";
        fileName = "Blurred Text3.jpg";
        try {
//            Not working that well !!!
            Mat source = Imgcodecs.imread(fileName,
                    //                    Imgcodecs.CV_LOAD_IMAGE_COLOR);
                    Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
            Mat destination = new Mat(source.rows(), source.cols(), source.type());
            Imgproc.GaussianBlur(source, destination, new Size(0, 0), 10);
            // The following was used witht he cat
//            Core.addWeighted(source, 1.5, destination, -0.75, 0, destination);
//            Core.addWeighted(source, 2.5, destination, -1.5, 0, destination);
            Core.addWeighted(source, 1.5, destination, -0.75, 0, destination);
            Imgcodecs.imwrite("sharpenedCat.jpg", destination);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Adapted from: http://www.tutorialspoint.com/java_dip/enhancing_image_brightness.htm
    public void enhanceImageBrightness() {
        double alpha = 1;   // Change to 2 for more brightness
        double beta = 50;
        String fileName = "cat.jpg";

        Mat source = Imgcodecs.imread("cat.jpg");
        Mat destination = new Mat(source.rows(), source.cols(),
                source.type());
        source.convertTo(destination, -1, 1, 50);
        Imgcodecs.imwrite("brighterCat.jpg", destination);
    }

    public void resizeImage() {
        Mat source = Imgcodecs.imread("cat.jpg");
        Mat resizeimage = new Mat();
        Imgproc.resize(source, resizeimage, new Size(250, 250));
        Imgcodecs.imwrite("resizedCat.jpg", resizeimage);
    }

    public void convertImage() {
        Mat source = Imgcodecs.imread("cat.jpg");
        // The extension determines the format
        Imgcodecs.imwrite("convertedCat.jpg", source);
        Imgcodecs.imwrite("convertedCat.jpeg", source);
        Imgcodecs.imwrite("convertedCat.webp", source);
        Imgcodecs.imwrite("convertedCat.png", source);
        Imgcodecs.imwrite("convertedCat.tiff", source);
    }

    public void noiseRemoval() {
//        Mat Kernel = cv::Mat(cv::Size(Maximum_Width_of_Noise,Maximum_Height_of_noise),CV_8UC1,cv::Scalar(255));        
        Mat Kernel = new Mat(new Size(3, 3), CvType.CV_8U, new Scalar(255));
        Mat source = Imgcodecs.imread("noiseExample.png");
        Mat temp = new Mat();
        Mat topHat = new Mat();
        Mat destination = new Mat();

        Imgproc.morphologyEx(source, temp, Imgproc.MORPH_OPEN, Kernel);
        Imgproc.morphologyEx(temp, destination, Imgproc.MORPH_CLOSE, Kernel);
//        Imgproc.morphologyEx(temp, topHat, Imgproc.MORPH_GRADIENT, Kernel);
//        Imgproc.morphologyEx(topHat, destination, Imgproc.MORPH_CLOSE, Kernel);
        Imgcodecs.imwrite("noiseRemovedExample.png", source);
    }

    public static void denoise() {
        String imgInPath = "captchaExample.jpg";
        imgInPath = "MyCaptcha.PNG";
        imgInPath = "blurredtext.jpg";
        String imgOutPath = "captchaNoiseRemovedExample.png";
        imgOutPath = "MyNoiseRemovedCaptcha.PNG";

        Mat image = Imgcodecs.imread(imgInPath);
        Mat out = new Mat();
        Mat tmp = new Mat();
        Mat kernel = new Mat(new Size(3, 3), CvType.CV_8UC1, new Scalar(255));
//        Mat kernel = new Mat(image.size(), CvType.CV_8UC1, new Scalar(255));
        Imgproc.morphologyEx(image, tmp, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(tmp, out, Imgproc.MORPH_CLOSE, kernel);
        Imgcodecs.imwrite(imgOutPath, out);
    }

//    public void convertToTIFF() {
//        Mat source = Imgcodecs.imread("OCRExample.png");
//        Imgcodecs.imwrite("OCRExample.tiff", source);
//    }
}
