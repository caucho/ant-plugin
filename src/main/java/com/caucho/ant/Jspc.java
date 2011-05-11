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

import java.io.*;
import java.util.*;
import java.util.logging.Level;

import org.apache.tools.ant.*;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.*;

/**
 * Command-line tool and ant task to compile jsp files
 */
public class Jspc extends Task {
  private File _rootDirectory;
  private File _resinHome;
  private Vector _classpath = new Vector();
  protected Level _level = Level.WARNING;

  /**
   * For ant.
   **/
  public Jspc()
  {
  }

  public void setRootDirectory(File root)
  {
    _rootDirectory = root;
  }

  public void addClasspath(Path path)
  {
    _classpath.add(path);
  }

  public File getResinHome()
  {
    return _resinHome;
  }

  public void setResinHome(File resinHome)
  {
    _resinHome = resinHome;
  }

  public Level getLevel()
  {
    return _level;
  }

  public void setLevel(Level level)
  {
    _level = level;
  }

  public void setLogLevel(String level)
  {
    if (level == null || level.isEmpty())
      return;

    level = level.toUpperCase(Locale.ENGLISH);

    _level = Level.parse(level);
  }

  /**
   * Executes the ant task.
   **/
  public void execute()
    throws BuildException
  {
    if (_resinHome == null)
      throw new BuildException("resinHome is required by jspc");

    if (_rootDirectory == null)
      throw new BuildException("rootDirectory is required by jspc");

    File resinJar = new File(_resinHome,
                             "lib" + File.separatorChar + "resin.jar");

    if (! resinJar.exists())
      throw new BuildException("resinHome '"
                                 + _resinHome
                                 + "' does not appear to be valid");

    Java java = new Java(this);
    java.setFailonerror(true);
    java.setFork(true);
    java.setJar(resinJar);

    List<String> args = new ArrayList<String>();
    args.add("jspc");
    args.add("-app-dir");
    args.add(_rootDirectory.getPath());

    for (String arg : args)
      java.createArg().setLine(arg);

    log(java.getCommandLine().toString(), _level.intValue());

    java.executeJava();
  }
}
