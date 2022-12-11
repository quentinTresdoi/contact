package comparer;
import java.util.Comparator;
import model.Contact;

public class comparercontact implements Comparator<Contact> {
	@Override
	public int compare(Contact n1, Contact n2) {
		return n1.getDateNaissance().compareTo(n2.getDateNaissance());
	}
}