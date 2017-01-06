package packt;

import static java.lang.System.out;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

// Adapted from http://docs.opencv.org/2.4/doc/tutorials/introduction/desktop_java/java_dev_intro.html

public class DetectFaceDemo {
    
  public void run() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    String base = "C:/Books in Progress/Java for Data Science/Chapter 10/OpenCVExamples/src/resources";
    CascadeClassifier faceDetector = 
            new CascadeClassifier(base + "/lbpcascade_frontalface.xml");
    
    Mat image = Imgcodecs.imread(base + "/images.jpg");

    MatOfRect faceVectors = new MatOfRect();
    faceDetector.detectMultiScale(image, faceVectors);

    out.println(faceVectors.toArray().length + " faces found");

    for (Rect rect : faceVectors.toArray()) {
        Imgproc.rectangle(image, new Point(rect.x, rect.y), 
                new Point(rect.x + rect.width, rect.y + rect.height), 
                new Scalar(0, 255, 0));
    }
    Imgcodecs.imwrite("faceDetection.png", image);
  }
}
