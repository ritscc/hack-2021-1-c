package dev.abelab.timestamp.db.entity;

import java.util.Date;

/**
 * Stamp Sample Builder
 */
public class StampSample extends AbstractSample {

	public static StampSampleBuilder builder() {
		return new StampSampleBuilder();
	}

	public static class StampSampleBuilder {

		private Integer id = SAMPLE_INT;
		private String title = SAMPLE_STR;
		private String description = SAMPLE_STR;
		private Integer userId = SAMPLE_INT;
		private Date createdAt = SAMPLE_DATE;
		private Date updatedAt = SAMPLE_DATE;

		public StampSampleBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public StampSampleBuilder title(String title) {
			this.title = title;
			return this;
		}

		public StampSampleBuilder description(String description) {
			this.description = description;
			return this;
		}

		public StampSampleBuilder userId(Integer userId) {
			this.userId = userId;
			return this;
		}

		public StampSampleBuilder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public StampSampleBuilder updatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public Stamp build() {
			return Stamp.builder() //
				.id(this.id) //
				.title(this.title) //
				.description(this.description) //
				.userId(this.userId) //
				.createdAt(this.createdAt) //
				.updatedAt(this.updatedAt) //
				.build();
		}

	}

}
