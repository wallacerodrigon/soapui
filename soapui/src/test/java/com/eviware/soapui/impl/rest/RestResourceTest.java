/*
 *  SoapUI, copyright (C) 2004-2012 smartbear.com
 *
 *  SoapUI is free software; you can redistribute it and/or modify it under the
 *  terms of version 2.1 of the GNU Lesser General Public License as published by 
 *  the Free Software Foundation.
 *
 *  SoapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details at gnu.org.
 */

package com.eviware.soapui.impl.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.eviware.soapui.impl.rest.support.RestParamProperty;
import com.eviware.soapui.utils.ModelItemFactory;
import junit.framework.JUnit4TestAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import org.junit.internal.matchers.TypeSafeMatcher;

public class RestResourceTest
{
	@Test
	public void shouldGetTemplateParams() throws Exception
	{
		WsdlProject project = new WsdlProject();
		RestService restService = ( RestService )project.addNewInterface( "Test", RestServiceFactory.REST_TYPE );
		RestResource resource = restService.addNewResource( "Resource", "/test" );

		assertEquals( resource.getDefaultParams().length, 0 );

		resource.setPath( "/{id}/test" );
		assertEquals( resource.getDefaultParams().length, 0 );
		assertEquals( "/{id}/test", resource.getFullPath() );

		RestResource subResource = resource.addNewChildResource( "Child", "{test}/test" );
		assertEquals( "/{id}/test/{test}/test", subResource.getFullPath() );
	}

	@Test
	public void shouldIgnoreMatrixParamsOnPath() throws Exception
	{
		WsdlProject project = new WsdlProject();
		RestService restService = ( RestService )project.addNewInterface( "Test", RestServiceFactory.REST_TYPE );
		RestResource resource = restService.addNewResource( "Resource", "/test" );
		resource.setPath( "/maps/api/geocode/xml;Param2=matrixValue2;address=16" );

		// asserts full path does not have the matrix params
		assertEquals( "/maps/api/geocode/xml", resource.getFullPath() );

		RestResource subResource = resource.addNewChildResource( "Child", "{test}/test/version;ver=2" );

		// asserts child resources's path does not have the matrix params
		assertEquals( "{test}/test/version", subResource.getPath() );

		// asserts child resources's full path does not have the matrix params
		assertEquals( "/maps/api/geocode/xml/{test}/test/version", subResource.getFullPath() );
	}

	@Ignore("Not consistent with current model, but this is how it should work!")
	@Test
	public void alwaysAddsParametersLastInFullPath() throws Exception
	{
		String parameterName = "some_param_name";
		String parameterValue = "the_very_special_value";
		RestResource parentResource = ModelItemFactory.makeRestResource();
		parentResource.setPath( "/parent" );
		RestParamProperty parameter = parentResource.addProperty( parameterName );
		parameter.setValue( parameterValue );
		RestResource childResource = parentResource.addNewChildResource( "child", "the_child" );

		//TODO: Replace with the new CommonMatchers.endsWith() method when this is merged back
		String matrixParametersString = ";" + parameterName + "=" + parameterValue;
		assertThat(childResource.getFullPath(), endsWith(matrixParametersString));
	}



	private Matcher<String> endsWith(final String suffix)
	{
		return new TypeSafeMatcher<String>()
		{
			@Override
			public boolean matchesSafely( String s )
			{
				return s.endsWith( suffix );
			}

			@Override
			public void describeTo( Description description )
			{
				description.appendText( "a string ending with " + suffix );
			}
		};
	}
}