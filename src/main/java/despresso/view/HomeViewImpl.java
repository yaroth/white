package despresso.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import despresso.Views;
import despresso.logic.CalendarEntry;
import despresso.presenter.ObserverInterface;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
public class HomeViewImpl extends VerticalLayout implements SubjectInterface<ObserverInterface> {

    private VerticalLayout mainMoodArea = new VerticalLayout();
    private VerticalLayout mainCalendarArea = new VerticalLayout();
    private List<ObserverInterface> listeners = new ArrayList<>();
    private MoodViewImpl moodView;
    private CalendarViewImpl calendarView;
    private Label calendarTitleLabel;
    private Label calendarContentLabel;
    private Label calendarTitle;
    private CalendarEntry entry = null;

    private Button calendarConfirmButton;

    private Label label;

    public HomeViewImpl(MoodViewImpl moodView, CalendarViewImpl calendarView) {
        this.moodView = moodView;
        this.calendarView = calendarView;
        label = new Label();
        initView();
    }

    private void initView() {
        //adds MoodView
        mainMoodArea.add(moodView);

        //adds CalendarView
        calendarTitle = new Label("Your next appointment:");
        loadNextDueCalendarEntry();
        mainCalendarArea.add(calendarTitle, calendarTitleLabel, calendarContentLabel, calendarConfirmButton);
        this.add(mainMoodArea, mainCalendarArea);

    }

    private void loadNextDueCalendarEntry() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        List<CalendarEntry> nextCalendarEntries = calendarView.getNextCalendarEntriesSorted();
        if (!nextCalendarEntries.isEmpty()) {
            entry = nextCalendarEntries.get(0);
            calendarTitleLabel = new Label("Titel: " + entry.getTitle());
            LocalDateTime dueDate = entry.getStart();
            calendarContentLabel = new Label(("Termin um: " + dueDate.format(formatter)));
            calendarConfirmButton = createButton(Views.DONE);
            calendarConfirmButton.setVisible(true);
        } else {
            calendarTitleLabel = new Label("No appointment in the next hours.");
            calendarContentLabel = new Label("Have fun and enjoy life to the fullest!");
            calendarConfirmButton = new Button();
            calendarConfirmButton.setVisible(false);
        }
    }

    @Override
    public void removeObserver(ObserverInterface observer) {
        listeners.remove(observer);
    }

    @Override
    public void addObserver(ObserverInterface observer) {
        listeners.add(observer);

    }

    private Button createButton(Views view) {
        if (view.getIcon() != null){
            return new Button(view.getIcon(), event -> this.registerObject(event));
        } else {
            return new Button(view.toString(), event -> this.registerObject(event));
        }
    }

    private void registerObject(ClickEvent event) {
        for (ObserverInterface listener : listeners) {
            if (event.getSource().equals(calendarConfirmButton)) {
                listener.update(Views.DONE.toString());
            }
        }
    }

    public void loadMoodView() {
        mainMoodArea.removeAll();
        mainMoodArea.add(moodView);
    }

    // Reset the home view
    public void resetView() {
        remove(mainMoodArea, mainCalendarArea);
        mainMoodArea = new VerticalLayout();
        mainCalendarArea = new VerticalLayout();
        moodView.resetView();
        initView();
    }

    public void closeCalendarEntry() {
        calendarView.deleteCalendarEntry(entry);
        resetView();
    }
}
