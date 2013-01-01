package cz.muni.fi.pa165.vozovna.editors;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lukas Hajek
 */
public class DateListEditor extends PropertyEditorSupport {

    private String dateFormat;

    public static final String DEFAULT_DATE_PATERN = "yyyy-MM-dd";
    
    public DateListEditor(String dateFormat) {
        super();
        setDateFormat(dateFormat);
    }

    public DateListEditor() {
        super();
        setDateFormat(null);
    }

    /**
     * Sets date pattern
     * 
     * @param dateFormat Date pattern
     * @see java.util.Date
     */
    public final void setDateFormat(String dateFormat) {
        if (dateFormat == null) {
            this.dateFormat = DEFAULT_DATE_PATERN;
        } else {
            this.dateFormat = dateFormat;
        }
    }
    
    
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            String[] lines = text.split("\n");
            SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
            List<Date> dates = new LinkedList<>();
            for (String line : lines) {
                if (line.equals("")) {
                    continue;
                }

                try {
                    dates.add(((Date) sdf.parse(line)));
                } catch (ParseException ex) {
                }
            }

            setValue(dates);
        }
    }

    @Override
    public String getAsText() {
        List<Date> dates = (List<Date>) getValue();
        if (dates == null) {
            return null;
        } else {
            StringBuilder asText = new StringBuilder();

            SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
            for (Date date : dates) {
                asText.append(sdf.format(date)).append("\n");
            }
            return asText.toString();
        }
    }
}
