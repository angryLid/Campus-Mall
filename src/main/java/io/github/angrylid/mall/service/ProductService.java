package io.github.angrylid.mall.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.request.PostProductDto;
import io.github.angrylid.mall.dto.response.ProductDetailsDTO;
import io.github.angrylid.mall.generated.entity.Favorite;
import io.github.angrylid.mall.generated.entity.Product;
import io.github.angrylid.mall.generated.entity.Relation;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.FavoriteMapper;
import io.github.angrylid.mall.generated.mapper.ProductMapper;
import io.github.angrylid.mall.generated.mapper.RelationMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.utils.Minio;

@Service
public class ProductService {

    private Minio minio;
    private ProductMapper productMapper;
    private UserMapper userMapper;
    private FavoriteMapper favoriteMapper;
    private RelationMapper relationMapper;

    @Autowired
    public ProductService(Minio minio, ProductMapper productMapper, UserMapper userMapper,
            FavoriteMapper favoriteMapper, RelationMapper relationMapper) {
        this.minio = minio;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.favoriteMapper = favoriteMapper;
        this.relationMapper = relationMapper;
    }

    /**
     * 管理员 检索所有商品
     * 
     * @return
     */
    public List<Product> selectProducts() {
        Page<Product> pages = productMapper.selectPage(new Page<Product>(1, 50),
                new QueryWrapper<Product>().orderByDesc("created_at"));
        return pages.getRecords();
    }

    /**
     * 管理员 更新状态(违规/撤销)
     * 
     * @param productId
     * @param status
     */
    public Integer updateProductStatus(Integer id, Integer status) {
        Product product = new Product();
        product.setStatus(status);
        product.setId(id);
        return productMapper.updateById(product);
    }

    /**
     * 检索商品,分页
     */
    public List<Product> selectProductPage(Integer index) {
        Page<Product> pages = productMapper.selectPage(new Page<Product>(index, 2),
                new QueryWrapper<Product>().orderByDesc("created_at"));
        return pages.getRecords();
    }

    /**
     * 插入一条产品记录
     * 
     * @param dto
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void addProduct(PostProductDto dto, Integer id) throws IllegalAccessException, IOException {
        var entity = new Product();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setSellerId(id);
        entity.setPrice(new BigDecimal(dto.getPrice()));
        entity.setStatus(1);
        if (dto.getpType().equals("personal")) {
            entity.setpType(true);
        } else if (dto.getpType().equals("enterprise")) {
            entity.setpType(false);
        } else {
            entity.setpType(null);
        }

        for (MultipartFile file = dto.getImage0(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage0(name);
        }
        for (MultipartFile file = dto.getImage1(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage1(name);
        }
        for (MultipartFile file = dto.getImage2(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage2(name);
        }
        for (MultipartFile file = dto.getImage3(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage3(name);
        }
        for (MultipartFile file = dto.getImage4(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage4(name);
        }
        for (MultipartFile file = dto.getImage5(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage5(name);
        }

        try {
            productMapper.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 检索所有店家的商品
     * 
     * @return 店家商品列表
     */
    public List<Product> selectRtrProducts() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_type", 0);
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索所有店家的商品 排除自己的商品
     * 
     * @param id
     * @return
     */
    public List<Product> selectRtrProducts(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("seller_id", id);

        queryWrapper.eq("p_type", 0);
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索所有个人的商品
     * 
     * @return 商品列表
     */
    public List<Product> selectUserProducts() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_type", 1);
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索所有商品 排除用户自己的商品
     * 
     * @param id
     * @return
     */
    public List<Product> selectUserProducts(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("seller_id", id);
        queryWrapper.eq("p_type", 1);
        queryWrapper.orderByDesc("created_at");
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 检索商品发布者的联系方式
     * 
     * @param productId
     * @return
     */
    public String selectSellerTel(Integer productId) {
        Product product = productMapper.selectById(productId);
        Integer sellerId = product.getSellerId();
        String tel = userMapper.selectById(sellerId).getTelephone();
        return tel;
    }

    public Product getProduct(String id) {
        return productMapper.selectById(id);
    }

    public ProductDetailsDTO getProductAndSeller(String id) {
        ProductDetailsDTO ret = new ProductDetailsDTO();

        Product product = getProduct(id);
        Integer sellerId = product.getSellerId();
        User user = userMapper.selectById(sellerId);

        ret.setId(product.getId());
        ret.setSellerId(user.getId());
        ret.setSellerName(user.getNickname());
        ret.setSellerTel(user.getTelephone());
        ret.setTitle(product.getTitle());
        ret.setDescription(product.getDescription());
        ret.setPrice(product.getPrice().toString());
        ret.setPublishTime(product.getCreatedAt().toString());
        ret.setImage0(product.getImage0());
        ret.setImage1(product.getImage1());
        ret.setImage2(product.getImage2());
        ret.setImage3(product.getImage3());
        ret.setImage4(product.getImage4());
        ret.setImage5(product.getImage5());

        return ret;
    }

    public ProductDetailsDTO getProductAndSeller(Integer userId, String productId) {
        ProductDetailsDTO p = getProductAndSeller(productId);

        Product product = getProduct(productId);
        Boolean followedSeller = relationMapper.selectCount(
                new QueryWrapper<Relation>().eq("user_id", userId).eq("follower_id", product.getSellerId())) > 0;

        p.setFollowedSeller(followedSeller);
        Favorite favorite = favoriteMapper
                .selectOne(new QueryWrapper<Favorite>().eq("user_id", userId).eq("product_id", productId));
        p.setFavorite(favorite != null);

        return p;
    }

    public List<Product> selectMyPublished(Integer id) {
        return productMapper.selectList(new QueryWrapper<Product>().eq("seller_id", id));
    }

    public Integer deleteProduct(Integer id) {
        return productMapper.deleteById(id);
    }

    public List<Product> selectFavorite(Integer id) {
        List<Favorite> favorites = favoriteMapper.selectList(new QueryWrapper<Favorite>().eq("user_id", id));
        List<Product> products = new ArrayList<>();
        for (Favorite favorite : favorites) {
            products.add(productMapper.selectById(favorite.getProductId()));
        }
        return products;
    }

    public Integer addFavorite(Integer userId, Integer productId) {
        return 1;
    }

}
