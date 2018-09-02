package org.qian.commons.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name="t_test")
@Entity
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @GeneratedValue(generator = "snowFlakeId")
    @GenericGenerator(name = "snowFlakeId", strategy = "org.qian.commons.db.entity.SnowflakeIdGenerator")
    @Id
    @Column(name = "id")
    @ApiModelProperty("ID")
    private String id;


    @LastModifiedDate
    @Column(name = "update_date")
    @ApiModelProperty("更新时间")
    private Date updateDate;

    @LastModifiedBy
    @Column(name = "update_by")
    @ApiModelProperty("更新人")
    private String updateBy;

    @CreatedDate
    @Column(name = "created_date")
    @ApiModelProperty("创建时间")
    private Date createdDate;

    @CreatedBy
    @Column(name = "created_by")
    @ApiModelProperty("创建人")
    private String createdBy;

}