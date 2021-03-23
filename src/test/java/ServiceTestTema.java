import org.junit.jupiter.api.Test;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTestTema {
    //create new xlm file
    //assign to service to avoid collisions
    //service returns 1 if validator founds a problem (possible fix?)
    //service returns 0 if duplicate found

    private final Service service = new Service(
            new StudentXMLRepository(new StudentValidator(), "studenti.xml"),
            new TemaXMLRepository(new TemaValidator(), "teme.xml"),
            new NotaXMLRepository(new NotaValidator(), "note.xml"));

    @Test
    void test_saveTema_success() {
        String id = "4";
        int result = service.saveTema(id,"descriere",5,2);
        assertEquals(result,1);
        service.deleteTema(id);
    }

    @Test
    void test_saveTema_duplicate() {
        String id = "4";
        service.saveTema(id,"descriere",5,2);
        int result = service.saveTema(id,"descriere",5,2);
        assertEquals(result,0);
        service.deleteTema(id);
    }
}