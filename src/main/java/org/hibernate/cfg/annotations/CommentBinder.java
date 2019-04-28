/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.cfg.annotations;

import org.hibernate.annotaions.Comment;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.cfg.Ejb3Column;
import org.hibernate.mapping.PersistentClass;

/**
 * TODO
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/5 14:47
 */

public class CommentBinder {

    /**
     * bindTableComment
     * @param clazzToProcess
     * @param persistentClass
     */
    public static void bindTableComment(XClass clazzToProcess, PersistentClass persistentClass) {
        if (clazzToProcess.isAnnotationPresent(Comment.class)) {
            String tableComment = clazzToProcess.getAnnotation(Comment.class).value();
            persistentClass.getTable().setComment(tableComment);
        } else {
            throw new RuntimeException(String.format("Must add a leave param the table, annotated with `@Leave`: %s", clazzToProcess.getName()));
        }
    }

    /**
     * bindColumnComment
     * @param property
     * @param columns
     */
    public static void bindColumnComment(XProperty property, Ejb3Column[] columns) {
        if (null != columns) {
            if (property.isAnnotationPresent(Comment.class)) {
                String comment = property.getAnnotation(Comment.class).value();
                for (Ejb3Column column : columns) {
                    column.getMappingColumn().setComment(comment);
                }
            } else {
                throw new RuntimeException(String.format("Must add a leave param the table field, annotated with `@Leave`: %s.%s", property.getDeclaringClass().getName(), property.getName()));
            }
        }
    }

    /**
     * bindColumnComment
     * @param property
     * @param column
     */
    public static void bindColumnComment(XProperty property, Ejb3Column column) {
        if (null != column) {
            if (property.isAnnotationPresent(Comment.class)) {
                String comment = property.getAnnotation(Comment.class).value();

                column.getMappingColumn().setComment(comment);

            }
        }
    }

}
