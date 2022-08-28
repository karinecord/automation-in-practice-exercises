package org.automation;

import org.automation.pages.SkeletonsContentPlaceholdersPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SkeletonTest extends BaseTest{
    @Autowired
    private SkeletonsContentPlaceholdersPage skeletonPage;

    @Test
    public void displaySkeletonsContent(){
        skeletonPage.visit();

        assertThat(skeletonPage.isSkeletonVisible()).isTrue();

        skeletonPage.waitSkeletonToBeInvisible();
    }
}
