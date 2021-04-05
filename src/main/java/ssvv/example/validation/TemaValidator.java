package ssvv.example.validation;
import ssvv.example.domain.Tema;

public class TemaValidator implements Validator<Tema> {
    public void validate(Tema tema) throws ValidationException {
        if (tema.getID() == null || tema.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        if (tema.getDescriere() == null || tema.getDescriere().equals("")) {
            throw new ValidationException("Descriere invalida! \n");
        }
        if (tema.getDeadline() < 1 || tema.getDeadline() > 14) {
            throw new ValidationException("Deadline invalid! \n");
        }
        if (tema.getStartline() < 1 || tema.getStartline() > 14) {  //condition reduntant
            throw new ValidationException("Data de primire invalida! \n");
        }
        if(tema.getDeadline() < tema.getStartline()){
            throw new ValidationException("Deadline mai mic sau egal ca startline! \n");
        }
    }
}

