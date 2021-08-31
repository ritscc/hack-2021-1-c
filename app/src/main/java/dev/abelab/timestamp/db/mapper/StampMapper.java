package dev.abelab.timestamp.db.mapper;

import java.util.List;

import dev.abelab.timestamp.db.mapper.base.StampBaseMapper;
import dev.abelab.timestamp.db.entity.Stamp;
import dev.abelab.timestamp.db.entity.join.StampWithAttachments;

public interface StampMapper extends StampBaseMapper {

    void bulkInsert(List<Stamp> stamps);

    List<StampWithAttachments> selectAllWithAttachments();

}
