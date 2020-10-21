package com.ohlc;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JunitRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(FineStateMachineTest.class);
		for (Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
		System.out.println("Result==" + result.wasSuccessful());
	}
}
