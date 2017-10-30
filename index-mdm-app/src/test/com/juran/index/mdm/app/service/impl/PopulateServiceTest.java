package com.juran.index.mdm.app.service.impl;

import com.juran.index.mdm.app.IndexMdmApplication;
import com.juran.index.mdm.app.service.PopulateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IndexMdmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PopulateServiceTest {

    @Autowired
    private PopulateService populateService;

    @Test
    public void fullPopulateCatalog() throws Exception {


        populateService.fullPopulate();
    }

    @Test
    public void deltaImport() throws Exception {

//        populateService.deltaPopulate();
    }

    @Test
    public void catalogHierarchyDataPreProcessor() throws Exception {
        populateService.processorCatgroupPath();
    }

    @Test
    public void processorCatgroupPath() throws Exception {
        populateService.processorCatgroupPath();
    }
}
