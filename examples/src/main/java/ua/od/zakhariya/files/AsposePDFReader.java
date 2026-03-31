package ua.od.zakhariya.files;
import com.aspose.pdf.License;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextAbsorber;
import com.aspose.pdf.XImage;

public class AsposePDFReader {
    public static void main(String[] args) throws Exception {//main() function for HowToReadPDFFileInJava

        // Instantiate the license to remove trial version restrictions while reading the PDF file
        License license = new License();
//        license.setLicense("Aspose.PDF.lic");
        license.setLicense("Aspose.PDF.Java.lic");

        // Load the PDF file from which text and images are to be read
        Document pdf = new Document("Input.pdf");

        // 1. Read entire text from the PDF file
        // Instantiate a TextAbsorber Class object to read Text from PDF file
        TextAbsorber textAbsorberObject = new TextAbsorber();

        // Call PageCollection.accept() method to let TextAbsorber find text in PDF Pages
        pdf.getPages().accept(textAbsorberObject);

        // Write the extracted text from the sample PDF to console
        System.out.println(textAbsorberObject.getText());

        // 2. Extract images from PDF file
        int imageCount = 1;

        // Iterate through all the PDF pages to access images collection and save them on the disc
        for (Page pdfPage : pdf.getPages())
        {
            // Iterate through images collection in the PDF file
            for (XImage image : pdfPage.getResources().getImages())
            {
                java.io.FileOutputStream outputImageFromPdfFile = new java.io.FileOutputStream(pdfPage.getNumber() + "-"+ imageCount+"-output.jpg");

                // Save each image in the PDF file images collection to a JPG file
                image.save(outputImageFromPdfFile);
                outputImageFromPdfFile.close();

                imageCount++;
            }

            // Reset image index
            imageCount = 1;
        }
    }
}
