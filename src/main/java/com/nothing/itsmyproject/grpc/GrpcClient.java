package com.nothing.itsmyproject.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import search.SearchServiceGrpc;
import search.Search;

@Service
public class GrpcClient {

    private static final Logger logger = LoggerFactory.getLogger(GrpcClient.class);
    private final SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;

    public GrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();
        searchServiceBlockingStub = SearchServiceGrpc.newBlockingStub(channel);
    }

    public Search.SearchUserResponse grpcSearchUser(String username) {
        logger.info("Sending gRPC request to search user with username: {}", username);
        Search.SearchUserRequest request = Search.SearchUserRequest.newBuilder()
                .setUsername(username)
                .build();
        Search.SearchUserResponse response = searchServiceBlockingStub.grpcSearchUser(request);
        logger.info("Received gRPC response: {}", response);
        System.out.println("Received gRPC response: " + response);
        return response;
    }

    public Search.SearchProductResponse grpcSearchProduct(String productName) {
        logger.info("Sending gRPC request to search product with productName: {}", productName);
        Search.SearchProductRequest request = Search.SearchProductRequest.newBuilder()
                .setProductName(productName)
                .build();
        Search.SearchProductResponse response = searchServiceBlockingStub.grpcSearchProduct(request);
        logger.info("Received gRPC response: {}", response);
        System.out.println("Received gRPC response: " + response);
        return response;
    }
}