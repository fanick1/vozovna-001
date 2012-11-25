package cz.muni.fi.pa165.vozovna.editors;

import java.beans.PropertyEditorSupport;

import org.joda.time.DateTime;

/**
 * @author Eva Neduchalová, 359893
 */
public class DateTimeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            DateTime dateTime = DateTime.parse(text);
            setValue(dateTime);
        }
    }

    @Override
    public String getAsText() {
        DateTime dateTime = (DateTime) getValue();
        if (dateTime == null) {
            return null;
        } else {
            return dateTime.toString("YYYY-MM-dd");
        }
    }

}
