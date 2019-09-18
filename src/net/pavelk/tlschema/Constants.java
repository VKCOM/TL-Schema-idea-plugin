/*
 * Copyright (C)
 *     2015-2019 Pavel Kunyavskiy
 *     2016-2019 Eugene Kurpilyansky
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package net.pavelk.tlschema;public class Constants {
    public static String[] validAnnotations = new String[]{"@read", "@write", "@readwrite", "@any", "@internal"};
    public static String[] rwAnnotations = new String[]{"@read", "@write", "@readwrite", "@any"};
}
