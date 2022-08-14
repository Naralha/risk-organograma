package io.sld.riskcomplianceservice.domain.service.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Filter class for {@link java.time.ZonedDateTime} type attributes.
 *
 * @see RangeFilter
 */
public class ZonedDateTimeFilter extends RangeFilter<ZonedDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ZonedDateTimeFilter.</p>
     */
    public ZonedDateTimeFilter() {
    }

    /**
     * <p>Constructor for ZonedDateTimeFilter.</p>
     *
     * @param filter a {@link ZonedDateTimeFilter} object.
     */
    public ZonedDateTimeFilter(ZonedDateTimeFilter filter) {
        super(filter);
    }

    /** {@inheritDoc} */
    @Override
    public ZonedDateTimeFilter copy() {
        return new ZonedDateTimeFilter(this);
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setEquals(ZonedDateTime equals) {
        super.setEquals(equals);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setNotEquals(ZonedDateTime equals) {
        super.setNotEquals(equals);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setIn(List<ZonedDateTime> in) {
        super.setIn(in);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setNotIn(List<ZonedDateTime> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setGreaterThan(ZonedDateTime equals) {
        super.setGreaterThan(equals);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setLessThan(ZonedDateTime equals) {
        super.setLessThan(equals);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setGreaterThanOrEqual(ZonedDateTime equals) {
        super.setGreaterThanOrEqual(equals);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ZonedDateTimeFilter setLessThanOrEqual(ZonedDateTime equals) {
        super.setLessThanOrEqual(equals);
        return this;
    }

}

