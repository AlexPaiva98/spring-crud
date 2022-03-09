package br.ufrn.imd.springcrud.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeClassHelper<E> {
    public Class<E> getGenericClass() throws ClassNotFoundException {
        Type mySuperclass = getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
        String className = tType.getTypeName();
        return (Class<E>) Class.forName(className);
    }
}
