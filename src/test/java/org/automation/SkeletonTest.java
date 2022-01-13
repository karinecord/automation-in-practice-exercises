package org.automation;

import org.automation.pages.SkeletonsContentPlaceholdersPage;
import org.automation.pages.ToDoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SkeletonTest extends BaseTest{
    @Autowired
    private SkeletonsContentPlaceholdersPage skeletonPage;

    @Test
    public void displaySkeletonsContent(){
        skeletonPage.visit();
        skeletonPage.isElementDisplayed(skeletonPage.skeletonsContent,1000);
        assertThat(skeletonPage.isElementDisplayed(skeletonPage.skeletonsContent,1000)).isTrue();
    }
}
