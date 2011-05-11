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
 * Ant task to deploy war files to resin
 */
public class DeployTask extends AbstractDeployTask
{
  private String _warFile;

  /**
   * For ant.
   */
  public DeployTask()
  {
  }

  public void setWarFile(String warFile)
    throws BuildException
  {
    if (!warFile.endsWith(".war"))
      throw new BuildException("war-file must have .war extension");

    _warFile = warFile;

    if (getContext() == null) {
      int lastSlash = _warFile.lastIndexOf("/");

      if (lastSlash < 0)
        lastSlash = 0;

      int end = _warFile.length() - ".war".length();
      String name = _warFile.substring(lastSlash, end);

      setContext(name);
    }
  }

  @Override
  protected void validate()
    throws BuildException
  {
    super.validate();

    if (_warFile == null)
      throw new BuildException("war-file is required by " + getTaskName());
  }

  @Override protected void fillArgs(List<String> args)
  {
    args.add("deploy");
    fillBaseArgs(args);

    if (getStage() != null) {
      args.add("-stage");
      args.add(getStage());
    }

    if (getVersion() != null) {
      args.add("-version");
      args.add(getVersion());
    }

    if (getHost() != null) {
      args.add("-host");
      args.add(getHost());
    }

    args.add("-name");
    args.add(getContext());

    args.add(_warFile);
  }
}
