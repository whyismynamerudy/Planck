package Dashboard;

import java.time.LocalDate;

public class HomeworkTask {

    //local variables used in this class
    private String title;
    private LocalDate dueDate;
    private String priority;

    //The following are all Accessors and Mutators

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
