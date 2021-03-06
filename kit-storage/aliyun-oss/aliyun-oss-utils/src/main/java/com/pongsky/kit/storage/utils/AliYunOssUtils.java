package com.pongsky.kit.storage.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.UploadPartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 阿里云 OSS 工具类
 *
 * @author pengsenhao
 */
public class AliYunOssUtils {

    /**
     * endpoint
     */
    private final String endpoint;

    /**
     * bucket
     */
    private final String bucket;

    /**
     * accessKeyId
     */
    private final String accessKeyId;

    /**
     * accessKeySecret
     */
    private final String accessKeySecret;

    /**
     * OSS client
     */
    private final OSS client;

    public AliYunOssUtils(String endpoint, String bucket, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.bucket = bucket;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.client = getClient();
        this.createBucket();
    }

    /**
     * 创建 client
     *
     * @return 创建 client
     */
    private OSS getClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 创建 bucket（如果不存在，则自动创建）
     * <p>
     * <a href="https://help.aliyun.com/document_detail/32012.html">创建存储空间</a>
     */
    public void createBucket() {
        // 判断 bucket 是否存在
        boolean isExists = client.doesBucketExist(bucket);
        if (isExists) {
            return;
        }
        // 不存在则创建
        CreateBucketRequest request = new CreateBucketRequest(bucket)
                // 设置 bucket 策略为公共读
                .withCannedACL(CannedAccessControlList.PublicRead);
        client.createBucket(request);
    }

    /**
     * 文件上传
     * <p>
     * <a href="https://help.aliyun.com/document_detail/84781.html">简单上传</a>
     *
     * @param fileName    文件名称
     * @param inputStream input 流
     * @return 文件访问路径
     */
    public String upload(String fileName, InputStream inputStream) {
        PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream);
        try {
            client.putObject(request);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "/" + fileName;
    }

    /**
     * 获取分片上传事件ID
     * <p>
     * <a href="https://help.aliyun.com/document_detail/84786.html">分片上传</a>
     *
     * @param fileName 文件名称
     * @return 分片上传事件ID
     */
    public String initPartUpload(String fileName) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucket, fileName);
        return client.initiateMultipartUpload(request).getUploadId();
    }

    /**
     * 分片上传
     * <p>
     * <a href="https://help.aliyun.com/document_detail/84786.html">分片上传</a>
     *
     * @param uploadId    分片上传事件ID
     * @param partNumber  当前分片数
     * @param partSize    当前分片文件大小，单位为字节，譬如：1MB = 1 * 1024 * 1024L
     * @param fileName    文件名称
     * @param inputStream input 流
     * @return 分片信息
     */
    public PartETag partUpload(String uploadId, int partNumber, long partSize, String fileName, InputStream inputStream) {
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(bucket);
        uploadPartRequest.setKey(fileName);
        uploadPartRequest.setUploadId(uploadId);
        uploadPartRequest.setPartSize(partSize);
        uploadPartRequest.setInputStream(inputStream);
        uploadPartRequest.setPartNumber(partNumber);
        try {
            return client.uploadPart(uploadPartRequest).getPartETag();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 列举已上传的分片
     * <p>
     * <a href="https://help.aliyun.com/document_detail/84786.html">分片上传</a>
     *
     * @param uploadId 分片上传事件ID
     * @param fileName 文件名称
     * @return 分片信息列表
     */
    public List<PartETag> listPart(String uploadId, String fileName) {
        ListPartsRequest request = new ListPartsRequest(bucket, fileName, uploadId);
        PartListing partListing = client.listParts(request);
        return partListing.getParts().stream()
                .map(p -> new PartETag(p.getPartNumber(), p.getETag()))
                .collect(Collectors.toList());
    }

    /**
     * 合并分片上传
     * <p>
     * <a href="https://help.aliyun.com/document_detail/84786.html">分片上传</a>
     *
     * @param uploadId 分片上传事件ID
     * @param fileName 文件名称
     * @param parts    分片信息列表
     * @return 文件访问路径
     */
    public String completePartUpload(String uploadId, String fileName, List<PartETag> parts) {
        CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest(bucket, fileName, uploadId, parts);
        client.completeMultipartUpload(request);
        return "/" + fileName;
    }

}
