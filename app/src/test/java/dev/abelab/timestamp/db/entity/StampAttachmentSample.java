package dev.abelab.timestamp.db.entity;

import java.util.Date;
import java.util.UUID;

/**
 * Stamp Attachment Sample Builder
 */
public class StampAttachmentSample extends AbstractSample {

	public static StampAttachmentSampleBuilder builder() {
		return new StampAttachmentSampleBuilder();
	}

	public static class StampAttachmentSampleBuilder {

		private Integer id = SAMPLE_INT;
		private Integer stampId = SAMPLE_INT;
		private String name = SAMPLE_STR;
		private String uuid = UUID.randomUUID().toString();
		private Date createdAt = SAMPLE_DATE;
		private Date updatedAt = SAMPLE_DATE;

		public StampAttachmentSampleBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public StampAttachmentSampleBuilder stampId(Integer stampId) {
			this.stampId = stampId;
			return this;
		}

		public StampAttachmentSampleBuilder name(String name) {
			this.name = name;
			return this;
		}

		public StampAttachmentSampleBuilder uuid(String uuid) {
			this.uuid = uuid;
			return this;
		}

		public StampAttachmentSampleBuilder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public StampAttachmentSampleBuilder updatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public StampAttachment build() {
			return StampAttachment.builder() //
				.id(this.id) //
				.stampId(this.stampId) //
				.name(this.name) //
				.uuid(this.uuid) //
				.createdAt(this.createdAt) //
				.updatedAt(this.updatedAt) //
				.build();
		}

	}

}
