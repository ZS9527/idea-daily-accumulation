/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotaions;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * TODO
 *
 * @author ifzm
 * @version 0.1
 * @date 2019/3/4 21:12
 */

@Documented
@Target({ TYPE, FIELD })
@Retention(RUNTIME)
public @interface Comment {

    /**
     * sql leave
     * @return sql leave
     */
    String value();

}
