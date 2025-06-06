package org.qubership.profiler.sax.string;

import org.qubership.profiler.util.ProfilerConstants;
import org.qubership.profiler.sax.raw.TraceVisitor;
import org.qubership.profiler.sax.raw.TreeRowid;
import org.qubership.profiler.sax.raw.TreeTraceVisitor;

public class StringTraceAdapter extends TraceVisitor {
    private final StringRepositoryAdapter ra;

    public StringTraceAdapter(StringRepositoryAdapter ra, TraceVisitor tv) {
        this(ProfilerConstants.PROFILER_V1, ra, tv);
    }

    protected StringTraceAdapter(int api, StringRepositoryAdapter ra, TraceVisitor tv) {
        super(api, tv);
        this.ra = ra;
    }

    @Override
    public StringTreeTraceAdapter visitTree(TreeRowid rowid) {
        TreeTraceVisitor ttv = super.visitTree(rowid);
        if (ttv == null)
            return null;
        return new StringTreeTraceAdapter(ra, ttv);
    }

    public StringTraceAdapter asSkipVisitEnd() {
        return new StringTraceAdapter(api, ra, this) {
            @Override
            public void visitEnd() {
                // No operation
            }

            @Override
            public StringTraceAdapter asSkipVisitEnd() {
                return this;
            }
        };
    }
}
