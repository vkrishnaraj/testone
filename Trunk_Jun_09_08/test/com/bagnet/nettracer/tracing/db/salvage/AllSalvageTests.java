package com.bagnet.nettracer.tracing.db.salvage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestSalvage.class,
			   TestSalvageBox.class,
			   TestSalvageItem.class,
			   TestSalvageOHDReference.class})
public class AllSalvageTests { }
