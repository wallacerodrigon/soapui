/**
 * 
 */
package com.eviware.soapui.security;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviware.soapui.config.SecurityCheckConfig;
import com.eviware.soapui.config.SecurityTestConfig;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.security.check.GroovySecurityCheck;
import com.eviware.soapui.security.check.SecurityCheck;

/**
 * @author dragica.soldo
 * 
 */
public class SecurityTestRunnerTest
{

	WsdlTestCase testCase;
	SecurityTestConfig config = SecurityTestConfig.Factory.newInstance();
	HashMap<String, List<SecurityCheck>> securityChecksMap = new HashMap<String, List<SecurityCheck>>();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		WsdlProject project = new WsdlProject( "D:" + File.separatorChar + "temp" + File.separatorChar + "dragica"
				+ File.separator + "sample-soapui-project.xml" );
		TestSuite testSuite = project.getTestSuiteByName( "Test Suite" );
		List<SecurityCheck> secCheckList = new ArrayList();
		GroovySecurityCheck gsc = new GroovySecurityCheck( SecurityCheckConfig.Factory.newInstance() );
		gsc.setScript( "log.info testStep" );
		secCheckList.add( gsc );
		securityChecksMap.put( "SEK to USD Test", secCheckList );
		testCase = ( WsdlTestCase )testSuite.getTestCaseByName( "Test Conversions" );

		// WsdlInterface iface = ( WsdlInterface )project.getInterfaceAt( 0 );
		//
		// WsdlMockService mockService = ( WsdlMockService
		// )project.addNewMockService( "MockService 1" );
		//
		// mockService.setPort( 9081 );
		// mockService.setPath( "/testmock" );
		//
		// WsdlOperation operation = ( WsdlOperation )iface.getOperationAt( 0 );
		// WsdlMockOperation mockOperation = ( WsdlMockOperation
		// )mockService.addNewMockOperation( operation );
		// WsdlMockResponse mockResponse = mockOperation.addNewMockResponse(
		// "Test Response", true );
		// mockResponse.setResponseContent( "Tjohoo!" );
		//
		// mockService.start();

		// iface.addEndpoint( "/testmock" );
		// WsdlRequest request = ( WsdlRequest )operation.getRequestAt( 0 );
		// TestStepResult result = testStep.run( testRunner, testRunContext );

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testStart()
	{
		// WsdlTestRequestStep testStep = ( WsdlTestRequestStep )
		// testCase.getTestStepByName("SEK to USD Test");

		SecurityTest securityTest = new SecurityTest( testCase, config );
		SecurityTestRunnerImpl testRunner = new SecurityTestRunnerImpl( securityTest );
		// SecurityTestContext testRunContext = new SecurityTestContext(
		// testRunner );

		testRunner.start();

		// assertEquals(TestStepResult.TestStepStatus.OK, wsdlResult.getStatus());

	}

}
