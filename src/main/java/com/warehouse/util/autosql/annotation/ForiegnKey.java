package com.warehouse.util.autosql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.warehouse.util.autosql.Model;
import com.warehouse.util.autosql.CascadeAction;

// Annotation for foreign key
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForiegnKey {
    Class<? extends Model> references();
    String referencedColumn();
    CascadeAction onDelete() default CascadeAction.NO_ACTION;
    CascadeAction onUpdate() default CascadeAction.NO_ACTION;
}
