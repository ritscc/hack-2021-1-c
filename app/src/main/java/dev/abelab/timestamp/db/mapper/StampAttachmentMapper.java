package dev.abelab.timestamp.db.mapper;

import java.util.List;

import dev.abelab.timestamp.db.mapper.base.StampAttachmentBaseMapper;
import dev.abelab.timestamp.db.entity.StampAttachment;

public interface StampAttachmentMapper extends StampAttachmentBaseMapper {

    void bulkInsert(List<StampAttachment> stampAttachments);

}
