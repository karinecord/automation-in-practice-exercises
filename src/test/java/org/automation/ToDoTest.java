package org.automation;

import org.automation.pages.ToDoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ToDoTest extends BaseTest {
    private static final String TASK_1 = "Mopping.";
    private static final String TASK_2 = "Make bed.";
    private static final String TASK_3 = "Clean sink.";
    private static final String TASK_4 = "Set table.";
    private static final String TASK_5 = "Sort clothes.";
    private static final String TASK_6 = "Do the washing.";
    private static final String TASK_MAX_100_CHARACTERS = "Recite positive affirmations, meditaion, exercise 30 minutes, plan your day, avoid snoozing and pray";
    private static final String TASK_MIN_100_CHARACTERS = "Have a cup of tea, Drink water and Review your goals.";

    @Autowired
    private ToDoPage todoPage;

    @Test
    public void addMultipleTasks() {
        todoPage.visit();
        todoPage.addMultipleTasks(TASK_1, TASK_2, TASK_3, TASK_4, TASK_5, TASK_6);
        assertThat(TASK_1).isEqualTo("Mopping.");
        assertThat(TASK_2).isEqualTo("Make bed.");
        assertThat(TASK_3).isEqualTo("Clean sink.");
        assertThat(TASK_4).isEqualTo("Set table.");
        assertThat(TASK_5).isEqualTo("Sort clothes.");
        assertThat(TASK_6).isEqualTo("Do the washing.");

    }

    @Test
    public void addOneTask() {
        todoPage.visit();
        todoPage.addOneTask(TASK_1);
        assertThat(TASK_1).contains("Mopping");
    }

    @Test
    public void removeOneTask() {
        todoPage.visit();
        todoPage.addOneTask(TASK_1);
        todoPage.removeOneTask();
        assertThat(todoPage.todo.contains(TASK_1)).isFalse();
    }

    @Test
    public void removeAllTasks() {
        todoPage.visit();
        addMultipleTasks();
        todoPage.removeAllTask();
        assertThat(todoPage.todo.contains(TASK_1)).isFalse();
        assertThat(todoPage.todo.contains(TASK_2)).isFalse();
        assertThat(todoPage.todo.contains(TASK_3)).isFalse();
        assertThat(todoPage.todo.contains(TASK_4)).isFalse();
        assertThat(todoPage.todo.contains(TASK_5)).isFalse();
        assertThat(todoPage.todo.contains(TASK_6)).isFalse();
    }

    @Test
    public void checkOneTask() {
        todoPage.visit();
        todoPage.addOneTask(TASK_1);
        todoPage.checkOneTask();
        assertThat(todoPage.checkboxTask.isSelected()).isTrue();
    }

    @Test
    public void checkAllTasks() {
        todoPage.visit();
        addMultipleTasks();
        todoPage.checkAllTasks();
        assertThat(todoPage.checkboxTask.isSelected()).isTrue();
    }

    @Test
    public void validateMaximumOf100Characters() {
        todoPage.visit();
        todoPage.addOneTask(TASK_MAX_100_CHARACTERS);
        assertThat(TASK_MAX_100_CHARACTERS).contains("Recite positive affirmations, meditaion, exercise 30 minutes, plan your day, avoid snoozing and pray");
    }

    @Test
    public void validateMinimumOf100Characters() {
        todoPage.visit();
        todoPage.addOneTask(TASK_MIN_100_CHARACTERS);
        assertThat(TASK_MIN_100_CHARACTERS).contains("Have a cup of tea, Drink water and Review your goals.");
    }
}


