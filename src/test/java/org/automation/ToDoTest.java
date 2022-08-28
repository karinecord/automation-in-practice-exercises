package org.automation;

import org.automation.pages.ToDoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ToDoTest extends BaseTest {
    private static final String TASK_MOPPING = "Mopping.";
    private static final String TASK_MAKE_BED = "Make bed.";
    private static final String TASK_CLEAR_SINK = "Clean sink.";
    private static final String TASK_SET_TABLE = "Set table.";
    private static final String TASK_SORT_CLOTHES = "Sort clothes.";
    private static final String TASK_DO_THE_WASHING = "Do the washing.";
    private static final String TASK_MORE_THAN_100_CHARACTERS = "Recite positive affirmations, meditation, exercise 30 minutes, plan your day, avoid snoozing and pray";

    @Autowired
    private ToDoPage todoPage;

    @BeforeMethod
    public void visitTodoPage() {
        todoPage.visit();
    }

    @Test
    public void testMaximumInputCharLimit() {
        todoPage.typeNewTask(TASK_MORE_THAN_100_CHARACTERS);
        assertThat(todoPage.getTodoAddNewTaskInputValue()).hasSize(100).isNotEqualTo(TASK_MORE_THAN_100_CHARACTERS);
    }

    @Test
    public void addMultipleTasks() {
        List<String> listTaskToBeAdded = List.of(
                TASK_MOPPING, TASK_MAKE_BED, TASK_CLEAR_SINK, TASK_SET_TABLE, TASK_SORT_CLOTHES, TASK_DO_THE_WASHING
        );
        for (String newTask : listTaskToBeAdded) {
            todoPage.addNewTask(newTask);
        }
        assertThat(todoPage.getAllTodoTasksText()).hasSameElementsAs(listTaskToBeAdded);
    }

    @Test
    public void addOneTask() {
        todoPage.addNewTask(TASK_MOPPING);

        assertThat(todoPage.getAllTodoTasksText()).hasSameElementsAs(List.of(TASK_MOPPING));
    }

    @Test
    public void removeOneTask() {
        todoPage.addNewTask(TASK_MOPPING);

        assertThat(todoPage.getAllTodoTasksText()).hasSameElementsAs(List.of(TASK_MOPPING));

        todoPage.removeTask(TASK_MOPPING);

        assertThat(todoPage.getAllTodoTasksText()).hasSameElementsAs(List.of());
        assertThat(todoPage.isNoTaskCreatedMessageVisible()).isTrue();
    }

    @Test
    public void removeAllTasks() {
        todoPage.addNewTask(TASK_MOPPING);
        todoPage.addNewTask(TASK_CLEAR_SINK);

        assertThat(todoPage.getAllTodoTasksText()).hasSameElementsAs(List.of(TASK_MOPPING, TASK_CLEAR_SINK));

        todoPage.removeAllTask();

        assertThat(todoPage.getAllTodoTasksText()).hasSameElementsAs(List.of());
        assertThat(todoPage.isNoTaskCreatedMessageVisible()).isTrue();
    }

    @Test
    public void checkOneTask() {
        todoPage.addNewTask(TASK_MOPPING);

        todoPage.checkTask(TASK_MOPPING);

        assertThat(todoPage.isTaskChecked(TASK_MOPPING)).isTrue();
    }

    @Test
    public void checkAllTasks() {
        List<String> listTaskToBeAdded = List.of(
                TASK_MOPPING, TASK_MAKE_BED, TASK_CLEAR_SINK, TASK_SET_TABLE, TASK_SORT_CLOTHES, TASK_DO_THE_WASHING
        );

        for (String newTask : listTaskToBeAdded) {
            todoPage.addNewTask(newTask);
        }

        for (String task : listTaskToBeAdded) {
            assertThat(todoPage.isTaskChecked(task)).isFalse();
        }

        for (String task : listTaskToBeAdded) {
            todoPage.checkTask(task);
        }

        for (String task : listTaskToBeAdded) {
            assertThat(todoPage.isTaskChecked(task)).isTrue();
        }
    }
}
