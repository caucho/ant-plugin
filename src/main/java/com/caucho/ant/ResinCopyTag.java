/*
 * Copyright 2011 Caucho Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.caucho.ant;

/**
 * Ant task to copy a tag in the repository.  A tag can be copied by
 * specifying the source tag explicitly using the "sourceTag" attribute
 * or using the "sourceStage", "sourceType", "sourceVirtualHost",
 * "sourceContextRoot", and "sourceVersion" attributes.  The target
 * tag can be specified explicitly using the "tag" attribute or by using
 * the "stage", "type", "virtualHost", "contextRoot", and "version"
 * attributes.
 */
@Deprecated
public class ResinCopyTag extends DeployCopyTask
{
}
