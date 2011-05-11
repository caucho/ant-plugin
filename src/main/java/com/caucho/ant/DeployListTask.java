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
 * Ant task to query tags of Resin applications deployed via the
 * ResinDeployWar task to production.
 */
public class DeployListTask extends AbstractDeployTask
{
  private String _pattern;
  private boolean _printValues = false;

  /**
   * For ant.
   **/
  public DeployListTask()
  {
  }

  public void setPattern(String pattern)
  {
    _pattern = pattern;
  }

  public String getPattern()
  {
    return _pattern;
  }

  public void setPrintValues(boolean printValues)
  {
    _printValues = printValues;
  }

  public boolean getPrintValues()
  {
    return _printValues;
  }

  @Override
  protected void validate()
    throws BuildException
  {
    super.validate();

    if (_pattern == null
        && getStage() == null
        && getHost() == null
        && getContext() == null
        && getVersion() == null)
      throw new BuildException("At least one of pattern, stage, virtualHost, contextRoot, or version is required by " + getTaskName());
  }

  @Override
  protected void fillArgs(List<String> args)
  {
    args.add("deploy-list");

    fillBaseArgs(args);

    if (_pattern != null) {
      args.add(_pattern);

      return;
    }

    StringBuilder pattern = new StringBuilder("^");
    if (_stage != null && ! _stage.isEmpty() && ! ".*".equals(_stage)) {
      pattern.append(_stage);
    } else {
      pattern.append("[^/]+");
    }

    pattern.append("/webapp/");

    if (_host != null && ! _host.isEmpty() && ! ".*".equals(_host)) {
      pattern.append(_host);
    } else {
      pattern.append("[^/]+");
    }

    boolean hasVersion = _version != null
      && !_version.isEmpty()
      && ! ".*".equals(_version);

    String version = null;

    if (hasVersion)
      version = _version.replace(".", "\\.");

    if (_context != null && ! _context.isEmpty() && ! ".*".equals(_context) && hasVersion) {
      pattern.append('/').append(_context).append("-").append(version).append(".*");
    } else if (_context != null && ! _context.isEmpty() && ! ".*".equals(_context)) {
      pattern.append('/').append(_context);
    } else if (hasVersion){
      pattern.append("/[^/]+-").append(version).append(".*");
    } else {
      pattern.append("/.*");
    }

    args.add(pattern.toString());
  }
}
