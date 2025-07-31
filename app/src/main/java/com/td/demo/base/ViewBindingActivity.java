package com.td.demo.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class ViewBindingActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        initView(binding);
    }

    protected abstract void initView(T binding);

    @SuppressWarnings("unchecked")
    private T getViewBinding() {
        try {
            Class<?> parent = getClass();
            while (parent.getSuperclass() != null && parent.getSuperclass() != ViewBindingActivity.class) {
                parent = parent.getSuperclass();
            }
            Class<T> bindingClass = (Class<T>) ((ParameterizedType) parent.getGenericSuperclass()).getActualTypeArguments()[0];
            Method inflateMethod = bindingClass.getMethod("inflate", LayoutInflater.class);
            return (T) inflateMethod.invoke(null, getLayoutInflater());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ViewBinding", e);
        }
    }
} 