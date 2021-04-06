import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTestNota {

    private static Service service;

    @BeforeAll
    public static void setUp() throws IOException {
        try {
            File testTemaXml = new File("testTema.xml");
            File testStudentXml = new File("testStudent.xml");
            File testNotaXml = new File("testNota.xml");
            if (testTemaXml.createNewFile()) {
                FileWriter myWriter = new FileWriter(testTemaXml.getName());
                FileWriter studWriter = new FileWriter(testStudentXml.getName());
                FileWriter notaWriter = new FileWriter(testNotaXml.getName());

                myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                myWriter.write("<Entitati>\n</Entitati>");
                myWriter.close();

                studWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                studWriter.write("<Entitati>\n</Entitati>");
                studWriter.close();

                notaWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                notaWriter.write("<Entitati>\n</Entitati>");
                notaWriter.close();
            } else {
                testTemaXml.delete();
                testStudentXml.delete();
                testNotaXml.delete();

                FileWriter myWriter = new FileWriter(testTemaXml.getName());
                FileWriter studWriter = new FileWriter(testStudentXml.getName());
                FileWriter notaWriter = new FileWriter(testNotaXml.getName());

                myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                myWriter.write("<Entitati>\n</Entitati>");
                myWriter.close();

                studWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                studWriter.write("<Entitati>\n</Entitati>");
                studWriter.close();

                notaWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                notaWriter.write("<Entitati>\n</Entitati>");
                notaWriter.close();
            }

        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        service = new Service(
                new StudentXMLRepository(new StudentValidator(), "testStudent.xml"),
                new TemaXMLRepository(new TemaValidator(), "testTema.xml"),
                new NotaXMLRepository(new NotaValidator(), "testNota.xml"));
    }
    void test_saveStudent_success(){
        int result = service.saveStudent("1","nume",933);
        assertEquals(1,result);
    }
    void test_saveTema_success(){
        int result = service.saveTema("1","desc",2,1);
        assertEquals(1,result);
    }
    void test_saveNota_success() {
        int result = service.saveNota("1","1",7.0,2,"feedback");
        assertEquals(result, 1);
    }

    @Test
    void test_integration_saveNota(){
        this.test_saveStudent_success();
        this.test_saveTema_success();
        this.test_saveNota_success();
    }

}