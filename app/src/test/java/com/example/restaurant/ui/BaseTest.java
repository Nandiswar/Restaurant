package com.example.restaurant.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest extends TestCase {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
}
