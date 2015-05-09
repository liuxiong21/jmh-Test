package org.freeman.mockito;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoTest {

	@Test
	public void testListMock() {
		// mock creation

		List mockedList = mock(List.class);

		// using mock object
		mockedList.add("one");
		mockedList.clear();

		// verification
		verify(mockedList).add("one2");
		verify(mockedList).clear();
	}

	@Test(expected = RuntimeException.class)
	public void testMockStub() {
		// You can mock concrete classes, not only interfaces
		LinkedList mockedList = mock(LinkedList.class);

		// stubbing
		when(mockedList.get(0)).thenReturn("first");
		when(mockedList.get(1)).thenThrow(new RuntimeException());

		// following prints "first"
		System.out.println(mockedList.get(0));

		// following throws runtime exception
		System.out.println(mockedList.get(1));

		// following prints "null" because get(999) was not stubbed
		System.out.println(mockedList.get(999));

		// Although it is possible to verify a stubbed invocation, usually it's
		// just redundant
		// If your code cares what get(0) returns then something else breaks
		// (often before even verify() gets executed).
		// If your code doesn't care what get(0) returns then it should not be
		// stubbed. Not convinced? See here.
		verify(mockedList).get(0);
	}

	@Test
	public void testArgmentMatch() {
		List mockedList = mock(List.class);
		// stubbing using built-in anyInt() argument matcher
		when(mockedList.get(anyInt())).thenReturn("element");

		// stubbing using hamcrest (let's say isValid() returns your own
		// hamcrest matcher):
		when(mockedList.contains(argThat(new IsValid()))).thenReturn(true);

		// following prints "element"
		System.out.println(mockedList.get(999));

		// you can also verify using an argument matcher
		verify(mockedList).get(anyInt());
	}

	@Test
	public void testMockInvocation() {
		List<String> mockedList = mock(List.class);

		// using mock
		mockedList.add("once");

		mockedList.add("twice");
		mockedList.add("twice");

		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times");

		// following two verifications work exactly the same - times(1) is used
		// by default
		verify(mockedList).add("once");
		verify(mockedList, times(1)).add("once");

		// exact number of invocations verification
		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(3)).add("three times");

		// verification using never(). never() is an alias to times(0)
		verify(mockedList, never()).add("never happened");
	}

	@Test
	public void testAnswer() {
		@SuppressWarnings("unchecked")
		List<Integer> mockList = mock(LinkedList.class);
		when(mockList.get(anyInt())).thenAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return "this is test";
			}

		});
		System.out.println(mockList.get(1000));
	}

	@Test
	public void testSpy() {
		List<String> list = new ArrayList<String>();
		List<String> mockList = mock(ArrayList.class);
		List<String> spyList = spy(list);
		spyList.add("11111");
		spyList.add("22222");
		Assert.assertEquals(0, list.size());
		Assert.assertEquals(2, spyList.size());

		//mockList.add("11111");
		//mockList.add("22222");
		when(mockList.get(1)).thenReturn("22222");
		mockList.add("11111");
		mockList.add("22222");
		Assert.assertEquals("22222", mockList.get(1));

		verify(mockList).add("11111");
		Assert.assertEquals("11111", spyList.get(0));
	}

	@Test
	public void testRealMethodAnswer() {
		List<String> mockList = mock(List.class, CALLS_REAL_METHODS);
		mockList.add("dddd");
		Assert.assertEquals("addd", mockList.get(0));
	}

	@Test
	public void testNoMoreInteraction() {
		List<String> mockList = mock(List.class);
		// interactions
		mockList.add("dddd");
		mockList.get(0);

		// verification
		verify(mockList).add("dddd");

		// following will fail because 'doSomethingUnexpected()' is unexpected
		verifyNoMoreInteractions(mockList);
	}
}

class IsValid implements Matcher<String> {

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean matches(Object item) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void describeMismatch(Object item, Description mismatchDescription) {
		// TODO Auto-generated method stub

	}

	@Override
	public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
		// TODO Auto-generated method stub

	}

}
