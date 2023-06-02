package com.hammerdev.validations;

import java.io.Serializable;

@FunctionalInterface
public interface ValidationFunction extends Serializable
{
	boolean get(Object value);
}
