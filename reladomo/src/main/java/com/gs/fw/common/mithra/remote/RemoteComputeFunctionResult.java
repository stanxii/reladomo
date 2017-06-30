/*
 Copyright 2016 Goldman Sachs.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

package com.gs.fw.common.mithra.remote;

import com.gs.fw.common.mithra.finder.Operation;
import com.gs.fw.common.mithra.finder.ResultSetParser;
import com.gs.fw.common.mithra.finder.orderby.OrderBy;

import java.io.ObjectOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.List;



public class RemoteComputeFunctionResult extends MithraRemoteResult
{

    private List resultList;
    private Operation op;
    private OrderBy orderby;
    private String columnOrFunctions;
    private ResultSetParser resultSetParser;

    public RemoteComputeFunctionResult(Operation op, OrderBy orderby, String columnOrFunctions, ResultSetParser resultSetParser)
    {
        this.op = op;
        this.orderby = orderby;
        this.columnOrFunctions = columnOrFunctions;
        this.resultSetParser = resultSetParser;
    }

    public RemoteComputeFunctionResult()
    {
        // for externalizable
    }

    public List getResultList()
    {
        return resultList;
    }

    public void writeExternal(ObjectOutput out) throws IOException
    {
        this.writeRemoteTransactionId(out);
        out.writeObject(resultList);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.readRemoteTransactionId(in);
        this.resultList = (List) in.readObject();
    }

    public void run()
    {
        this.resultList = op.getResultObjectPortal().computeFunction(op, orderby, columnOrFunctions, resultSetParser);
    }
}