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

import org.apache.tools.ant.BuildException;

import java.util.List;

/**
 * Ant task to delete a tag in the repository.  The tag may be specified
 * explicitly by the "tag" attribute or constructed using the stage,
 * type, host, context root, and version attributes.
 */
public class UndeployTask extends AbstractDeployTask
{
  private String _tag;

  /**
   * For ant.
   **/
  public UndeployTask()
  {
  }

  public void setTag(String tag)
  {
    _tag = tag;
  }

  @Override
  protected void validate()
    throws BuildException
  {
    super.validate();

    if (_tag == null && getContext() == null)
      throw new BuildException("tag or contextRoot is required by " +
                               getTaskName());
  }

  @Override
  protected void fillArgs(List<String> args)
  {
    args.add("undeploy");
    
    fillBaseArgs(args);

    if (_stage != null && ! _stage.isEmpty()) {
      args.add("-stage");
      args.add(_stage);
    }

    if (_host != null && ! _host.isEmpty()) {
      args.add("-host");
      args.add(_host);
    }

    if (_version != null && ! _version.isEmpty()) {
      args.add("-version");
      args.add(_version);
    }

    args.add(_context);
  }
}
